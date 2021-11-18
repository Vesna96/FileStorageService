package FileStorage.service1.controller;

import FileStorage.service1.constants.ApiResponsesConstants;
import FileStorage.service1.constants.FileServiceEndpoints;
import FileStorage.service1.controller.converter.ControllerConverter;
import FileStorage.service1.domain.model.*;
import FileStorage.service1.domain.usecase.*;
import FileStorage.service1.dto.response.responsedto.*;
import FileStorage.service1.dto.response.responsedto.ThunderStoreEmptyDataResponse;
import FileStorage.service1.dto.response.responsedto.ThunderStoreGenericDataResponse;
import FileStorage.service1.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static FileStorage.service1.constants.FileServiceEndpoints.*;


@RestController
@RequiredArgsConstructor
public class FileController {

    private final DownloadFileUseCase downloadFileUseCase;
    private final UploadFilesUseCase uploadFilesUseCase;
    private final GetFilesInfoUseCase getFilesInfoUseCase;
    private final DownloadZipUseCase getZipInfoUseCase;
    private final ControllerConverter controllerConverter;
    private final DeleteFilesUseCase deleteFilesUseCase;
    private final CreateUserFolderUseCase createUserFolderUseCase;
    private final GetUsersInOrgUseCase getUsersInOrgUseCase;
    private final DeleteAllFilesUseCase deleteAllFilesUseCase;

    private final JwtUtil jwtTokenUtil;

    @ApiOperation("Downloading one file")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 404, message = ApiResponsesConstants.API_MSG_404),
    })
    @GetMapping(DOWNLOAD_ONE_FILE_V1)
    public ResponseEntity<Resource> getFile(@PathVariable String filename,
                                            @RequestParam("folder") String folder,
                                            @RequestHeader("Authorization") String token,
                                            HttpServletRequest request) throws IOException
    {
        String jwt = token.substring(7);
        String email=jwtTokenUtil.extractEmail(jwt);
        int org=jwtTokenUtil.extractOrganizationId(jwt);

        ThunderStoreDownloadFileData downloadFileData = downloadFileUseCase.downloadFile(filename, org, email, folder, request);
        ThunderStoreDownloadFileDTO downloadFile = controllerConverter.toThunderStoreDownloadFilesDTO(downloadFileData);

        Resource resource = downloadFile.getResource();

        String contentType = null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation("Uploading a list of files")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 409, message = ApiResponsesConstants.API_MSG_409)
    })
    @PostMapping(path = FileServiceEndpoints.UPLOAD_FILES_V1)
    public ResponseEntity<ThunderStoreGenericDataResponse> uploadFiles(@RequestParam("files") List<MultipartFile> files,
                                                                       @RequestParam("folder") String folder,
                                                                       @RequestParam("bucket") String bucket,
                                                                       @RequestHeader("Authorization") String token)
    {
        String jwt = token.substring(7);
        String email=jwtTokenUtil.extractEmail(jwt);
        int org=jwtTokenUtil.extractOrganizationId(jwt);


        List<ThunderStoreUploadFileData> uploadFileData = uploadFilesUseCase.uploadFiles(files, org, email, folder, bucket);

        ThunderStoreUploadResponseDTO uploadedFiles = controllerConverter.toThunderStoreUploadResponseDTO(uploadFileData);

        return new ResponseEntity<>(ThunderStoreGenericDataResponse.builder()
                .message("Files uploaded successfully")
                .status(HttpStatus.OK.value())
                .data(uploadFileData)
                .build(), HttpStatus.OK);
    }

    @ApiOperation("Create a subfolder")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 409, message = ApiResponsesConstants.API_MSG_409)
    })
    @PostMapping(CREATE_SUBFOLDER)
    public ResponseEntity<ThunderStoreGenericDataResponse> createUserFolder(@RequestHeader("Authorization") String token,
                                                                            @RequestParam("folder") String folder,
                                                                            @RequestParam("bucket") String bucket)

    {
        String jwt = token.substring(7);
        int org=jwtTokenUtil.extractOrganizationId(jwt);
        String email = jwtTokenUtil.extractEmail(jwt);

        ThunderStoreCreateFolderData createFolderData = createUserFolderUseCase.createUserFolder(org, email, folder, bucket);
        ThunderStoreCreateFolderDTO createFolderDTO = controllerConverter.toThunderStoreCreateFolderDTO(createFolderData);

        return new ResponseEntity<>(ThunderStoreGenericDataResponse.builder()
                .message("Subfolder created!")
                .data(createFolderDTO)
                .status(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @ApiOperation("Returning a list of files in a folder")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 404, message = ApiResponsesConstants.API_MSG_404)
    })
    @GetMapping(GET_LIST_FILES_V1)
    public ResponseEntity<ThunderStoreGenericDataResponse> getListFiles(
            @RequestParam("folder") String folder,
            @RequestHeader("Authorization") String token)
    {
        String jwt = token.substring(7);
        String email = jwtTokenUtil.extractEmail(jwt);

        int org=jwtTokenUtil.extractOrganizationId(jwt);

        List<ThunderStoreGetFileInfoData> getFilesData = getFilesInfoUseCase.getFilesInfo(org, email, folder);
        ThunderStoreGetFilesInfoResponseDTO getFiles = controllerConverter.toThunderStoreGetFilesInfoResponseDTO(getFilesData);

        return new ResponseEntity<>(ThunderStoreGenericDataResponse.builder()
                .message("Files loaded successfully.")
                .status(HttpStatus.OK.value())
                .data(getFilesData)
                .build(), HttpStatus.OK);
    }

    @ApiOperation("Returning all users in the same organization")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
    })
    @GetMapping(GET_LIST_OF_USERS)
    public ResponseEntity<ThunderStoreGenericDataResponse> getListOfUsers(
            @RequestHeader("Authorization") String token
    )
    {
        String jwt = token.substring(7);
        int org=jwtTokenUtil.extractOrganizationId(jwt);

        List<ThunderStoreGetUsersInOrgData> getUsersInOrgData = getUsersInOrgUseCase.getUsersInOrg(org);
        ThunderStoreGetUsersInOrgResponseDTO getUsers = controllerConverter.toThunderStoreGetUsersInOrgResponseDTO(getUsersInOrgData);

        return new ResponseEntity<>(ThunderStoreGenericDataResponse.builder()
                .message("List of users in the same organization returned successfully.")
                .status(HttpStatus.OK.value())
                .data(getUsersInOrgData)
                .build(), HttpStatus.OK);
    }

    @ApiOperation("Deleting files from a bucket")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 404, message = ApiResponsesConstants.API_MSG_404)
    })
    @DeleteMapping(path = FileServiceEndpoints.DELETE_FILES_V1)
    public ResponseEntity<ThunderStoreEmptyDataResponse> deleteFiles(@RequestParam("files") List<String> files,
                                                                     @RequestParam("folder") String folder,
                                                                     @RequestParam("bucket") String bucket,
                                                                     @RequestHeader("Authorization") String token)
    {
        String jwt = token.substring(7);
        String email = jwtTokenUtil.extractEmail(jwt);

        int org=jwtTokenUtil.extractOrganizationId(jwt);

        deleteFilesUseCase.deleteFiles(files, org, email, folder, bucket);
        return new ResponseEntity<>(ThunderStoreEmptyDataResponse.builder()
                .message("Files deleted successfully")
                .status(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @ApiOperation("Deleting all files from a bucket and the bucket")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 404, message = ApiResponsesConstants.API_MSG_404)
    })
    @DeleteMapping(path = FileServiceEndpoints.DELETE_ALL_FILES_V1)
    public ResponseEntity<ThunderStoreEmptyDataResponse> deleteAll(@RequestParam("folder") String folder,
                                                                   @RequestParam("bucket") String bucket,
                                                                   @RequestParam("folderName") String folderName,
                                                                   @RequestHeader("Authorization") String token)
    {

        String jwt = token.substring(7);
        int org = jwtTokenUtil.extractOrganizationId(jwt);
        String email = jwtTokenUtil.extractEmail(jwt);

        deleteAllFilesUseCase.deleteAllFiles(org, folder, email, bucket, folderName);
        return new ResponseEntity<>(ThunderStoreEmptyDataResponse.builder()
                .message("Folder and its files deleted successfully.")
                .status(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @ApiOperation("Zip download")
    @ApiResponses({
            @ApiResponse(code = 200, message = ApiResponsesConstants.API_MSG_200),
            @ApiResponse(code = 404, message = ApiResponsesConstants.API_MSG_404)
    })
    @GetMapping(DOWNLOAD_ZIP_V1)
    public ResponseEntity<ThunderStoreEmptyDataResponse> downloadZip(
            HttpServletResponse response,
            @RequestParam("files") List<String> filename,
            @RequestParam("folder") String folder,
            @RequestHeader("Authorization") String token)
    {
        String jwt = token.substring(7);
        String email = jwtTokenUtil.extractEmail(jwt);

        int user=jwtTokenUtil.extractUserId(jwt);
        int org=jwtTokenUtil.extractOrganizationId(jwt);

        getZipInfoUseCase.downloadZip(response, filename, org, folder);

        return new ResponseEntity<ThunderStoreEmptyDataResponse>(ThunderStoreEmptyDataResponse.builder()
                .message("Zip download successful.")
                .status(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }
}