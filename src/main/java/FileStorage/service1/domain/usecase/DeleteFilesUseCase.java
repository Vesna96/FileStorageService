package FileStorage.service1.domain.usecase;

import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteFilesUseCase {

    private final ThunderStoreFilesRepository thunderStoreFilesRepository;

    public void deleteFiles(List<String> files, int org, String email, String folder, String bucket) {
        thunderStoreFilesRepository.deleteFiles(files, org, email, folder, bucket);
    }
}
