package FileStorage.service1.controller.converter;

import FileStorage.service1.domain.model.*;
import FileStorage.service1.dto.response.responsedto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ControllerConverter {

    public ThunderStoreDownloadFileDTO toThunderStoreDownloadFilesDTO(ThunderStoreDownloadFileData thunderStoreDownloadFileData) {
        return ThunderStoreDownloadFileDTO.builder()
                .resource(thunderStoreDownloadFileData.getResource())
                .build();
    }

    public ThunderStoreCreateFolderDTO toThunderStoreCreateFolderDTO(ThunderStoreCreateFolderData thunderStoreCreateFolderData) {
        return ThunderStoreCreateFolderDTO.builder()
                .foldername(thunderStoreCreateFolderData.getFoldername())
                .type(thunderStoreCreateFolderData.getType())
                .size(thunderStoreCreateFolderData.getSize())
                .build();
    }

    public ThunderStoreUploadResponseDTO toThunderStoreUploadResponseDTO(List<ThunderStoreUploadFileData> listOfFileData) {
        return ThunderStoreUploadResponseDTO.builder()
                .uploadedFiles(toListThunderStoreUploadFilesDTO(listOfFileData))
                .build();
    }

    public ThunderStoreGetFilesInfoResponseDTO toThunderStoreGetFilesInfoResponseDTO(List<ThunderStoreGetFileInfoData> listOfFileData) {
        return ThunderStoreGetFilesInfoResponseDTO.builder()
                .listOfFiles(toListThunderStoreGetFilesInfoDTO(listOfFileData))
                .build();
    }

    public ThunderStoreGetUsersInOrgResponseDTO toThunderStoreGetUsersInOrgResponseDTO(List<ThunderStoreGetUsersInOrgData> listOfUsersData) {
        return ThunderStoreGetUsersInOrgResponseDTO.builder()
                .listOfUserFolders(toListThunderStoreGetUsersInOrgDTO(listOfUsersData))
                .build();
    }

    private ThunderStoreUploadFilesDTO toThunderStoreUploadFilesDTO(ThunderStoreUploadFileData thunderStoreUploadFileData) {
        return ThunderStoreUploadFilesDTO.builder()
                .filename(thunderStoreUploadFileData.getFilename())
                .path(thunderStoreUploadFileData.getPath())
                .size(thunderStoreUploadFileData.getSize())
                .build();
    }

    private List<ThunderStoreUploadFilesDTO> toListThunderStoreUploadFilesDTO(List<ThunderStoreUploadFileData> listOfFileData) {
        return listOfFileData.stream()
                .map(this::toThunderStoreUploadFilesDTO)
                .collect(Collectors.toList());
    }

    private ThunderStoreGetFilesInfoDTO toThunderStoreGetFilesInfoDTO(ThunderStoreGetFileInfoData thunderStoreGetFileInfoData) {
        return ThunderStoreGetFilesInfoDTO.builder()
                .filename(thunderStoreGetFileInfoData.getFilename())
                .extension(thunderStoreGetFileInfoData.getExtension())
                .size(thunderStoreGetFileInfoData.getSize())
                .build();
    }

    private List<ThunderStoreGetFilesInfoDTO> toListThunderStoreGetFilesInfoDTO(List<ThunderStoreGetFileInfoData> listOfFileData) {
        return listOfFileData.stream()
                .map(this::toThunderStoreGetFilesInfoDTO)
                .collect(Collectors.toList());
    }

    private ThunderStoreGetUsersInOrgDTO toThunderStoreGetUsersInOrgDTO(ThunderStoreGetUsersInOrgData thunderStoreGetUsersInOrgData) {
        return ThunderStoreGetUsersInOrgDTO.builder()
                .folderName(thunderStoreGetUsersInOrgData.getFolderName())
                .build();
    }

    private List<ThunderStoreGetUsersInOrgDTO> toListThunderStoreGetUsersInOrgDTO(List<ThunderStoreGetUsersInOrgData> listOfUsersData) {
        return listOfUsersData.stream()
                .map(this::toThunderStoreGetUsersInOrgDTO)
                .collect(Collectors.toList());
    }
}
