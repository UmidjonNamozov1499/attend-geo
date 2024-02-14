package attend.geo.attend.service;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.SettingsDto;
import attend.geo.attend.entity.Settings;
import attend.geo.attend.payload.Payload;
import attend.geo.attend.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Service
public class SettingsService {
    private final SettingsRepository settingsRepository;

   public HttpEntity<?> createSettings(SettingsDto settingsDto) {
                    Settings settings = new Settings();
                    settings.setName(settingsDto.getName());
                    settings.setIp(settingsDto.getIp());
                    settings.setActive(settingsDto.getActive());

       Settings save = settingsRepository.save(settings);
       return Payload.ok(save).response();
    }

   public HttpEntity<?> getSettings(GetAllUserRequest request) {
        try {
            if (request != null) {
                Page<Settings> all = settingsRepository.findAll(PageRequest.of(request.getPageNumber(), request.getPageSize()));
                List<Settings> allSettings = all.stream().map(r ->
                        new Settings(
                                r.getId(),
                                r.getName(),
                                r.getIp(), r.getActive()
                        )).collect(Collectors.toList());
                if (allSettings != null) {
                    return Payload.ok(allSettings).response();
                } else {
                    return Payload.conflict("Settings not found").response();
                }
            } else {
                return Payload.conflict("Request error").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }

   public HttpEntity<?> getById(Long id) {
        try {
            Optional<Settings> byId = settingsRepository.findById(id);
            if (!byId.isEmpty()){
                Settings settings = byId.get();
                return Payload.ok(settings).response();
            }else {
                return Payload.conflict("Settings ID not found").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }

   public HttpEntity<?> editSettings(Long id, SettingsDto settingsDto) {
        try {
            Optional<Settings> byIP = settingsRepository.findById(id);
            if (!byIP.isEmpty()) {
                Settings settings = byIP.get();
                settings.setId(id);
                settings.setName(settingsDto.getName());
                settings.setIp(settingsDto.getIp());
                settings.setActive(settingsDto.getActive());
                settingsRepository.save(settings);
                return Payload.ok(settings).response();
            } else {
                return Payload.conflict("Id not found").response();
            }
        } catch (Exception e) {
            return Payload.conflict(e.getMessage()).response();
        }
    }

   public HttpEntity<?> deleteSetting(Long id){
        try {
            Optional<Settings> byId = settingsRepository.findById(id);
            if (!byId.isEmpty()) {
                settingsRepository.deleteById(id);
                return Payload.ok("Delete settings").response();
            }else {
                return Payload.conflict("Settings ID not found").response();
            }
        }catch (Exception e){
            return Payload.conflict(e.getMessage()).response();
        }
    }
}
