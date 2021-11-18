package FileStorage.service1.domain.usecase;

import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadZipUseCase
{
    private final ThunderStoreFilesRepository thunderStoreFilesRepository;

    public void downloadZip(HttpServletResponse response, List<String> filename, int org, String folder)
    {
        thunderStoreFilesRepository.downloadZipFiles(response, filename, org, folder);
    }
}
