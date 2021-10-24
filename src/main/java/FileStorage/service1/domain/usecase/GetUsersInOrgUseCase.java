package FileStorage.service1.domain.usecase;

import FileStorage.service1.domain.converter.UseCaseConverter;
import FileStorage.service1.domain.model.ThunderStoreGetUsersInOrgData;
import FileStorage.service1.persistence.model.ThunderStoreGetUsersInOrgEntity;
import FileStorage.service1.persistence.repository.ThunderStoreFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUsersInOrgUseCase {
    private final ThunderStoreFilesRepository thunderStoreFilesRepository;
    private final UseCaseConverter useCaseConverter;

    public List<ThunderStoreGetUsersInOrgData> getUsersInOrg(int org)
    {
        List<ThunderStoreGetUsersInOrgEntity> usersInOrgEntities = thunderStoreFilesRepository.getUsersInOrg(org);
        return useCaseConverter.toListThunderStoreGetUsersInOrgData(usersInOrgEntities);
    }

}
