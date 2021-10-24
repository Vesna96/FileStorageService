package FileStorage.service1.domain.usecase;

import FileStorage.service1.domain.converter.UseCaseConverter;
import FileStorage.service1.domain.model.ThunderStoreCreateFolderData;
import FileStorage.service1.persistence.model.ThunderStoreCreateFolderEntity;
import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserFolderUseCase {

    private final ThunderStoreFilesRepository thunderStoreFilesRepository;
    private final UseCaseConverter useCaseConverter;

    public ThunderStoreCreateFolderData createUserFolder(int org, String email, String folder, String bucket)
    {
        ThunderStoreCreateFolderEntity thunderStoreCreateFolderEntity=thunderStoreFilesRepository.createUserFolder(org, email, folder, bucket);
        return useCaseConverter.toThunderStoreCreateFolderData(thunderStoreCreateFolderEntity);
    }
}
