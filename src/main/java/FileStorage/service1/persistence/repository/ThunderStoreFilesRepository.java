package FileStorage.service1.persistence.repository;

import FileStorage.service1.exception.*;
import FileStorage.service1.persistence.model.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Repository
public class ThunderStoreFilesRepository {

    @Value("${upload.path}")
    private String uploadPath;
    private String currentDirectory = Paths.get("").toAbsolutePath().toString();

    public List<ThunderStoreUploadFileDataEntity> uploadFiles(List<MultipartFile> files, int org, String email, String folder, String bucket) {
        List<ThunderStoreUploadFileDataEntity> uploadedFiles = new ArrayList<>();
        String bucket1 = getBucketName(email);

        if(bucket.equals(""))
        {
            String directory = createDirectoryPath(org, "/"+bucket1);
            Path root = Paths.get(directory);

            files.forEach(file -> {
                uploadedFiles.add(uploadFile(file, directory, root));
            });
            return uploadedFiles;
        }

        if(bucket1.equals(bucket))
        {
            String directory = createDirectoryPath(org, folder);
            Path root = Paths.get(directory);

            files.forEach(file -> {
                uploadedFiles.add(uploadFile(file, directory, root));
            });

            return uploadedFiles;
        }
        else
        {
            throw ActionNotAllowedException
                    .builder().message("Action not allowed").build();

        }

    }

    public ThunderStoreCreateFolderEntity createUserFolder(int org, String email, String folder, String bucket)
    {
        String bucket1 = getBucketName(email);
        if(bucket1.equals(bucket))
        {
            int pos = folder.lastIndexOf('/');
            String newName = folder.substring(pos+1, folder.length());

            String directory = createDirectoryPath(org, folder);
            this.init(directory);

            return ThunderStoreCreateFolderEntity
                    .builder()
                    .foldername(newName)
                    .type("folder")
                    .size(0)
                    .build();
        }
        else
        {
            throw ActionNotAllowedException
                    .builder().message("Action not allowed").build();
        }
    }

    public ThunderStoreDownloadFileDataEntity load(String filename, int org, String email, String folder, HttpServletRequest request)
    {
        String bucket1 = getBucketName(email);

        try {
            Path file = Paths.get(currentDirectory + uploadPath, org+"/"+folder)
                    .resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists()) {

                return ThunderStoreDownloadFileDataEntity
                        .builder()
                        .resource(resource)
                        .build();
            } else {
                throw FileNotFoundException.builder()
                        .message("File not found").build();
            }
        } catch (MalformedURLException e) {
            throw FileNotFoundException.builder()
                    .message("File not found").build();
        }
    }

    public List<ThunderStoreGetFilesInfoEntity> getFiles(int org, String email, String folder) {
        String bucket = getBucketName(email);

        List<ThunderStoreGetFilesInfoEntity> fileInfos = this.loadAll(
                org, folder)
                .stream()
                .map(this::pathToFileData)
                .collect(Collectors.toList());

        return fileInfos;
    }

    public List<ThunderStoreGetUsersInOrgEntity> getUsersInOrg(int org) {
        List<ThunderStoreGetUsersInOrgEntity> userInOrgInfo = this.loadAllUserFolders(org)
                .stream()
                .map(this::pathToUsersInOrg)
                .collect(Collectors.toList());

        return userInOrgInfo;
    }

    public void downloadZipFiles(HttpServletResponse response, List<String> filename, int org, String folder)
    {
        List<String> fileNames = this.getFileList(filename, org, folder);

        FileSystemResource resource = null;
        try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
            for (String file : fileNames) {
                resource = new FileSystemResource(file);

                ZipEntry e = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
                e.setSize(resource.contentLength());
                e.setTime(System.currentTimeMillis());
                zippedOut.putNextEntry(e);
                StreamUtils.copy(resource.getInputStream(), zippedOut);
            }
            zippedOut.closeEntry();
            zippedOut.finish();
        } catch (IOException e) {
            throw FileLoadFailedException.builder().message("File load failed").build();
        }
    }

    public void deleteAllFiles(int org, String folder, String email, String bucket, String folderName) {
        Path directoryPath = Paths.get(createDirectoryPath(org, folder+"/"+folderName));
        String bucket1 = getBucketName(email);

        if(bucket1.equals(bucket))
        {
            if (!Files.exists(directoryPath))
            {
                throw DirectoryNotFoundException.builder()
                        .message("Directory doesn't exist.")
                        .build();
            }
        }
        else
        {
            throw ActionNotAllowedException
                    .builder().message("Action not allowed").build();
        }
        try {
            FileUtils.deleteDirectory(directoryPath.toFile());
        } catch (IOException e) {
            throw DeleteFileFailedException.builder()
                    .message("Cannot delete the files.")
                    .build();
        }
    }

    public void deleteFiles(List<String> files, int org, String email, String folder, String bucket) {
        String bucket1 = getBucketName(email);
        if(bucket1.equals(bucket))
        {
            checkIfExist(files, org, bucket, folder);

            files.forEach(file -> {
                deleteSingleFile(Paths.get(createDirectoryPath(org,folder) + file), file);
            });
        }
        else
        {
            throw ActionNotAllowedException
                    .builder().message("Action not allowed").build();
        }
    }

    private void deleteSingleFile(Path path, String... filename) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw DeleteFileFailedException.builder()
                    .message("Cannot delete " + filename + " the file.")
                    .build();
        }
    }

    private void checkIfExist(List<String> files, int org, String bucket, String folder) {
        files.forEach(file -> {
            if (!Files.exists(Paths.get(createDirectoryPath(org, folder) + file))) {
                throw FileNotFoundException.builder()
                        .message("File " + file + " not found in the bucket.")
                        .build();
            }
        });
    }

    private ThunderStoreGetFilesInfoEntity pathToFileData(Path path) {
        ThunderStoreGetFilesInfoEntity fileData = new ThunderStoreGetFilesInfoEntity();
        String filename = path.getFileName().toString();
        String extension= FilenameUtils.getExtension(filename);

        if(extension=="")
        {
            fileData.setExtension("folder");
        }
        else
        {
            fileData.setExtension(extension);
        }

        fileData.setFilename(filename);
        try {
            fileData.setSize((int) Files.size(path));
        } catch (IOException e) {
            throw FileLoadFailedException.builder().message("File load failed").build();
        }
        return fileData;
    }

    private ThunderStoreGetUsersInOrgEntity pathToUsersInOrg(Path path) {
        ThunderStoreGetUsersInOrgEntity userFolderData = new ThunderStoreGetUsersInOrgEntity();
        String folderName = path.getFileName().toString();
        userFolderData.setFolderName(folderName);

        return userFolderData;
    }

    private List<Path> loadAll(int org, String folder) {

        Path root = Paths.get(currentDirectory + uploadPath + "/" + org + "/"+ folder);
        try (Stream<Path> stream = Files.walk(root, 1))
        {
            if (Files.exists(root)) {
                return stream.filter(path -> !path.equals(root))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (IOException e) {
            throw DirectoryNotFoundException.builder()
                    .message("Directory not found.").build();
        }
    }

    private List<Path> loadAllUserFolders(int org) {

        Path root = Paths.get(currentDirectory + uploadPath + "/" + org + "/");
        try (Stream<Path> stream = Files.walk(root, 1)) {
            if (Files.exists(root)) {
                return stream.filter(path -> !path.equals(root))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (IOException e) {
            throw DirectoryNotFoundException.builder()
                    .message("Directory not found.").build();
        }
    }

    private List<String> getFileList(List<String> filename, int org, String folder) {
        List<String> files = new ArrayList<>();

        for (String f : filename) {
            String file = this.createDirectoryPath(org, folder) +f;
            files.add(file);
        }
        return files;
    }

    private String createDirectoryPath(int org, String folder) {
        return currentDirectory + uploadPath + "/" + org +folder+"/";
    }

    private void init(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw DirectoryAlreadyExistsException.builder()
                    .message("Could not create folder!").build();
        }
    }

    private void save(MultipartFile file, Path root, String filename) {

        try (InputStream inputStream = file.getInputStream()) {
            if (!Files.exists(root))
            {
                init(root.toString());
            }
            Files.copy(inputStream, root.resolve(filename));
        } catch (IOException e) {
            throw UploadFileFailedException.builder()
                    .message("Could not store the file " + file.getOriginalFilename() + " ." + " Error: " + e.getMessage())
                    .build();
        }
    }

    private String changeFileName(String oldName, String dir) {
        String newName;
        int i = 2;
        int pos = oldName.lastIndexOf('.');
        newName = oldName.substring(0, pos) + "(" + 1 + ")." + oldName.substring(pos + 1);
        String path = dir + "/" + newName;

        while (Files.exists(Paths.get(path))) {
            newName = oldName.substring(0, pos) + "(" + i + ")." + oldName.substring(pos + 1);
            path = dir + "/" + newName;
            i++;
        }
        return newName;
    }

    private ThunderStoreUploadFileDataEntity uploadFile(MultipartFile file, String directory, Path root) {

        String path = directory + file.getOriginalFilename();
        if (Files.exists(Paths.get(path))) {
            String fileName = changeFileName(file.getOriginalFilename(), directory);
            save(file, root, fileName);
            return ThunderStoreUploadFileDataEntity.builder()
                    .filename(fileName)
                    .path(directory + fileName)
                    .size(file.getSize())
                    .build();
        }
        save(file, root, file.getOriginalFilename());
        return ThunderStoreUploadFileDataEntity.builder()
                .filename(file.getOriginalFilename())
                .path(path)
                .size(file.getSize())
                .build();
    }

    private String getBucketName(String email) {
        int start = email.indexOf('@');
        String bucket = email.substring(0, start);
        return bucket;
    }
}






