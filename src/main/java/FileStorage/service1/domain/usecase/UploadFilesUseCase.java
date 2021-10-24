package FileStorage.service1.domain.usecase;

import FileStorage.service1.domain.converter.UseCaseConverter;
import FileStorage.service1.domain.model.ThunderStoreUploadFileData;
import FileStorage.service1.persistence.model.ThunderStoreUploadFileDataEntity;
import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadFilesUseCase {

    private final ThunderStoreFilesRepository thunderStoreFilesRepository;
    private final UseCaseConverter useCaseConverter;

    public List<ThunderStoreUploadFileData> uploadFiles(List<MultipartFile> files, int org, String email, String folder, String bucket)
    {
        List<ThunderStoreUploadFileDataEntity> uploadFileDataEntities = thunderStoreFilesRepository.uploadFiles(files, org, email, folder, bucket);
        return useCaseConverter.toListThunderStoreUploadFileData(uploadFileDataEntities);
    }
}
