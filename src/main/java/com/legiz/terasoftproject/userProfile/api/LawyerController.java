package com.legiz.terasoftproject.userProfile.api;

import com.legiz.terasoftproject.userProfile.domain.model.entity.Lawyer;
import com.legiz.terasoftproject.userProfile.domain.service.LawyerService;
import com.legiz.terasoftproject.userProfile.mapping.LawyerMapper;
import com.legiz.terasoftproject.userProfile.resource.CreateLawyerResource;
import com.legiz.terasoftproject.userProfile.resource.LawyerResource;
import com.legiz.terasoftproject.userProfile.resource.UpdateLawyerResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lawyers")
@RestController
@RequestMapping("/api/v1/lawyers")
public class LawyerController {

    private final LawyerService lawyerService;
    private final LawyerMapper mapper;

    public LawyerController(LawyerService lawyerService, LawyerMapper mapper) {
        this.lawyerService = lawyerService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get Lawyers", description = "Get All Lawyers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lawyer found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LawyerResource.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("")
    public Page<LawyerResource> getAllLawyer(Pageable pageable) {
        return mapper.modelListToPage(lawyerService.getAll(), pageable);
    }

    @Operation(summary = "Get Lawyer By Id", description = "Get Lawyer already stored by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lawyer found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LawyerResource.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("{lawyerId}")
    public LawyerResource getLawyerById(@PathVariable Long lawyerId) {
        return mapper.toResource(lawyerService.getById(lawyerId));
    }

    @Operation(summary = "Create Lawyer", description = "Create a new Lawyer")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lawyer created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CreateLawyerResource.class
                                    )
                            )
                    }
            )
    })
    @PostMapping
    public LawyerResource createLawyer(@RequestBody CreateLawyerResource request) {
        return mapper.toResource(lawyerService.create(mapper.toModel(request)));
    }

    @Operation(summary = "Update Lawyer", description = "Update Lawyer already stored by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lawyer updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UpdateLawyerResource.class
                                    )
                            )
                    }
            )
    })
    @PutMapping("{lawyerId}")
    public LawyerResource updateLawyer(@PathVariable Long lawyerId, @RequestBody UpdateLawyerResource request) {
        return mapper.toResource(lawyerService.update(lawyerId, mapper.toModel(request)));
    }

    @Operation(summary = "Delete Lawyer", description = "Delete Lawyer already stored")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lawyer deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @DeleteMapping("{lawyerId}")
    public ResponseEntity<?> deleteLawyer(@PathVariable Long lawyerId) {
        return lawyerService.delete(lawyerId);
    }
}
