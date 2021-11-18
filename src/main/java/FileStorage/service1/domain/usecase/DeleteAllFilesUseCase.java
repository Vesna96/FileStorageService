package FileStorage.service1.domain.usecase;

import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAllFilesUseCase {

    private final ThunderStoreFilesRepository thunderStoreFilesRepository;

    public void deleteAllFiles(int org, String folder, String email, String bucket, String folderName) {
        thunderStoreFilesRepository.deleteAllFiles(org, folder, email, bucket, folderName);
    }
}

