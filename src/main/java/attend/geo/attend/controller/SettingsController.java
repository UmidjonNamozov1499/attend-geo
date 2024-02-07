package attend.geo.attend.controller;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.SettingsDto;
import attend.geo.attend.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@ComponentScan("controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService settingsService;
    @PostMapping(value = "/createSettings")
    HttpEntity<?> createSettings(SettingsDto settingsDto){
        return settingsService.createSettings(settingsDto);
    }
    @GetMapping(value = "/getSettings")
    HttpEntity<?> getSettings(GetAllUserRequest request){
        return settingsService.getSettings(request);
    }
    @GetMapping(value = "/getSettings/{id}")
    HttpEntity<?> getSettingsId(@PathVariable Long id){
        return settingsService.getById(id);
    }
    @PutMapping(value = "/updateSettings/{id}")
    HttpEntity<?> updateSettings(@PathVariable Long id,
                                 @RequestBody SettingsDto settingsDto){
        return settingsService.editSettings(id,settingsDto);
    }
    @DeleteMapping(value = "/deleteSettings/{id}")
    HttpEntity<?> deleteSettings(@PathVariable Long id){
        return settingsService.deleteSetting(id);
    }
}
