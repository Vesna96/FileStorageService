package FileStorage.service1.domain.converter;

import FileStorage.service1.domain.model.*;
import FileStorage.service1.persistence.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UseCaseConverter {

    public ThunderStoreDownloadFileData toThunderStoreDownloadFileData(ThunderStoreDownloadFileDataEntity thunderStoreDownloadFileDataEntity) {
        return ThunderStoreDownloadFileData.builder()
                .resource(thunderStoreDownloadFileDataEntity.getResource())
                .build();
    }

    public ThunderStoreCreateFolderData toThunderStoreCreateFolderData(ThunderStoreCreateFolderEntity thunderStoreCreateFolderEntity) {
        return ThunderStoreCreateFolderData.builder()
                .foldername(thunderStoreCreateFolderEntity.getFoldername())
                .type(thunderStoreCreateFolderEntity.getType())
                .size(thunderStoreCreateFolderEntity.getSize())
                .build();
    }

    public List<ThunderStoreUploadFileData> toListThunderStoreUploadFileData(List<ThunderStoreUploadFileDataEntity> listOfFileDataEntities) {
        return listOfFileDataEntities.stream()
                .map(this::toThunderStoreUploadFileData)
                .collect(Collectors.toList());
    }

    public List<ThunderStoreGetFileInfoData> toListThunderStoreGetFilesInfoData(List<ThunderStoreGetFilesInfoEntity> listOfFileDataEntities) {
        return listOfFileDataEntities.stream()
                .map(this::toThunderStoreGetFileInfoData)
                .collect(Collectors.toList());
    }

    public List<ThunderStoreGetUsersInOrgData> toListThunderStoreGetUsersInOrgData(List<ThunderStoreGetUsersInOrgEntity> listOfUsersDataEntities) {
        return listOfUsersDataEntities.stream()
                .map(this::toThunderStoreGetUsersInOrgData)
                .collect(Collectors.toList());
    }

    private ThunderStoreUploadFileData toThunderStoreUploadFileData(ThunderStoreUploadFileDataEntity thunderStoreUploadFileDataEntity) {
        return ThunderStoreUploadFileData.builder()
                .filename(thunderStoreUploadFileDataEntity.getFilename())
                .path(thunderStoreUploadFileDataEntity.getPath())
                .size(thunderStoreUploadFileDataEntity.getSize())
                .build();
    }

    private ThunderStoreGetFileInfoData toThunderStoreGetFileInfoData(ThunderStoreGetFilesInfoEntity thunderStoreGetFilesInfoEntity) {
        return ThunderStoreGetFileInfoData.builder()
                .filename(thunderStoreGetFilesInfoEntity.getFilename())
                .size(thunderStoreGetFilesInfoEntity.getSize())
                .extension(thunderStoreGetFilesInfoEntity.getExtension())
                .build();
    }
    private ThunderStoreGetUsersInOrgData toThunderStoreGetUsersInOrgData(ThunderStoreGetUsersInOrgEntity thunderStoreGetUsersInOrgEntity) {
        return ThunderStoreGetUsersInOrgData.builder()
                .folderName(thunderStoreGetUsersInOrgEntity.getFolderName())
                .build();
    }

}
