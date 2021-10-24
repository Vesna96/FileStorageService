package FileStorage.service1.domain.usecase;

import FileStorage.service1.domain.converter.UseCaseConverter;
import FileStorage.service1.domain.model.ThunderStoreDownloadFileData;
import FileStorage.service1.persistence.model.ThunderStoreDownloadFileDataEntity;
import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class DownloadFileUseCase
{
    private final ThunderStoreFilesRepository thunderStoreFilesRepository;
    private final UseCaseConverter useCaseConverter;

    public ThunderStoreDownloadFileData downloadFile(String filename, int org, String email, String folder, HttpServletRequest request)
    {
        ThunderStoreDownloadFileDataEntity downloadFileDataEntities=thunderStoreFilesRepository.load(filename, org, email, folder, request);
        return useCaseConverter.toThunderStoreDownloadFileData(downloadFileDataEntities);
    }
}
