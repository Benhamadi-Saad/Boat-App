package com.example.boatApp.boat.domain;

import com.example.boatApp.boat.domain.exception.BoatDomainException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;


@Getter
@Setter
@NoArgsConstructor
public class Boat {

    private Long id;

    private String name;

    private String description;

    public void validate(){
        validateName();
        validateDescription();
    }

    private void validateName() {
        if (StringUtils.isBlank(name)) {
            throw new BoatDomainException("The boat should have a name");
        }
    }

    private void validateDescription() {
        if (StringUtils.isBlank(description)) {
            throw new BoatDomainException("The boat should have a description");
        }
    }


}
