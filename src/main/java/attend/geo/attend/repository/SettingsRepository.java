package attend.geo.attend.repository;

import attend.geo.attend.entity.Settings;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings,Long> {
    Optional<Settings> findByIp(String ip);

}
