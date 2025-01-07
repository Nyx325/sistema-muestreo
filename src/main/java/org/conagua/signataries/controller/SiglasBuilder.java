package org.conagua.signataries.controller;

import org.conagua.signataries.model.entity.ISignatary;

public class SiglasBuilder {
    public String generateSiglas(ISignatary s) {
        StringBuilder siglas = new StringBuilder();

        siglas.append(s.getFirstName().charAt(0));

        if (s.getMidName() != null)
            siglas.append(s.getMidName().charAt(0));

        siglas.append(s.getFatherLastname().charAt(0));

        if (s.getMidName() != null)
            siglas.append(s.getMotherLastname().charAt(0));

        return siglas.toString();
    }
}