package org.sprouts.digitalmusic.backend.controller;

import org.sprouts.digitalmusic.pojo.ResponseObject;

public abstract class AbstractController {

    protected ResponseObject getResponseObject(Integer output) {
        return new ResponseObject(output == 1 ? true : false, output == 1 ? "successful" : "failed");
    }
}
