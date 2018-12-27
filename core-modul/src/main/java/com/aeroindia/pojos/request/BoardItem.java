package com.aeroindia.pojos.request;

import java.io.Serializable;

/**
 * Created by dell1 on 19-09-2018.
 */

public class BoardItem implements Serializable {
    private String board_name;

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }
}
