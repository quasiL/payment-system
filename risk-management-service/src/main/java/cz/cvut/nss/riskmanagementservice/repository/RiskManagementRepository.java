package cz.cvut.nss.riskmanagementservice.repository;

import cz.cvut.nss.riskmanagementservice.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskManagementRepository extends JpaRepository<Request, Integer> {
}
