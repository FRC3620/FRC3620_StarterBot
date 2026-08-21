package org.usfirst.frc3620;

import java.util.List;

import io.avaje.jsonb.Json;

/**
 * Container for RobotParameters; designed to be subclassed.
 */
@Json
public class RobotParametersBase {
    @JsonProperty("serialNumber") 
    protected List<String> serialNumbers;
    protected boolean competitionRobot;
    protected String name;
    @JsonProperty("makeAllCANDevices") protected boolean makeAllCANDevices;

    public RobotParametersBase() {
        serialNumbers = List.of("");
        competitionRobot = false;
        name = "";
        makeAllCANDevices = false;
    }

    @SuppressWarnings("unused")
    public List<String> getSerialNumbers() {
        return serialNumbers;
    }

    public boolean isCompetitionRobot() {
        return competitionRobot;
    }

    public String getName() {
        return name;
    }

    public boolean shouldMakeAllCANDevices() {
        return makeAllCANDevices;
    }

    @Override
    public String toString() {
        return super.toString() + " [serial=" + serialNumbers + ", competitionRobot=" + competitionRobot + ", name="
                + name + ", makeAllCANDevices=" + makeAllCANDevices + "]";
    }
}