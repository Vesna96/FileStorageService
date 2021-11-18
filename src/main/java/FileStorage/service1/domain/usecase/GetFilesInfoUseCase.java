package FileStorage.service1.domain.usecase;

import FileStorage.service1.domain.converter.UseCaseConverter;
import FileStorage.service1.domain.model.ThunderStoreGetFileInfoData;
import FileStorage.service1.persistence.model.ThunderStoreGetFilesInfoEntity;
import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFilesInfoUseCase {

    private final ThunderStoreFilesRepository thunderStoreFilesRepository;
    private final UseCaseConverter useCaseConverter;

    public List<ThunderStoreGetFileInfoData> getFilesInfo(int org, String email, String folder)
    {
        List<ThunderStoreGetFilesInfoEntity> filesInfoEntities = thunderStoreFilesRepository.getFiles( org, email, folder);
        return useCaseConverter.toListThunderStoreGetFilesInfoData(filesInfoEntities);
    }
}
