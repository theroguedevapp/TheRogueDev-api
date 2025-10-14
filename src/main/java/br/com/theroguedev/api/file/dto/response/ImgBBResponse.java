package br.com.theroguedev.api.file.dto.response;

public record ImgBBResponse(
        ImgBBData data,
        boolean success,
        int status
) {
}
