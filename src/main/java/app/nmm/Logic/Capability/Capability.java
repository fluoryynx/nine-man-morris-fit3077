package app.nmm.Logic.Capability;

public enum Capability {
    PUT_TOKEN, // When actor inventory contain 9 token
    NORMAL, // When actor does not have any token in its inventory
    FLY, // When actor have less than 4 token on the board
}
