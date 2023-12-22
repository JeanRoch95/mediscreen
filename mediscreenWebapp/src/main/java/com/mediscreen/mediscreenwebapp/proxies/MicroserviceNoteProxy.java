package com.mediscreen.mediscreenwebapp.proxies;

import com.mediscreen.mediscreenwebapp.DTO.RiskLevelDTO;
import com.mediscreen.mediscreenwebapp.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * Proxy interface for communication with the Note microservice through the gateway service.
 */
@FeignClient(name = "gateway-service", url = "http://gatewayservice:9002")
public interface MicroserviceNoteProxy {

    /**
     * Retrieves all notes.
     *
     * @return A list of all NoteBean objects.
     */
    @GetMapping("api/notes")
    List<NoteBean> getAllNote();

    /**
     * Creates a new note.
     *
     * @param noteBean The NoteBean object containing the note information.
     * @return The created NoteBean object.
     */
    @PostMapping("api/notes")
    NoteBean createNote(@RequestBody NoteBean noteBean);

    /**
     * Retrieves all notes for a specific patient.
     *
     * @param id The ID of the patient.
     * @return A list of NoteBean objects associated with the specified patient.
     */
    @GetMapping("api/notes/{id}")
    List<NoteBean> getAllNotesById(@PathVariable long id);

    /**
     * Updates the risk level of notes for a specific patient.
     *
     * @param riskLevelDTO The DTO containing the patient ID and the new risk level.
     */
    @PostMapping("api/notes/updatesRiskLevel")
    void updateRiskLevel(@RequestBody RiskLevelDTO riskLevelDTO);

}

