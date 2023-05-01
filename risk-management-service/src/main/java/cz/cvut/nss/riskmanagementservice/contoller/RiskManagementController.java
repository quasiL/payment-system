package cz.cvut.nss.riskmanagementservice.contoller;

import cz.cvut.nss.riskmanagementservice.model.Request;
import cz.cvut.nss.riskmanagementservice.repository.RiskManagementRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RiskManagementController {

    private RiskManagementRepository riskManagementRepository;

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/requests")
    public List<Request> getRequests() {
        return riskManagementRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public void createRequest(@RequestBody Request request) {
        riskManagementRepository.save(request);
    }
}
