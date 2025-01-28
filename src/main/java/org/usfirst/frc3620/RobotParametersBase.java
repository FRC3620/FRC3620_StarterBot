package org.usfirst.frc3620;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Container for RobotParameters; designed to be subclassed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RobotParametersBase {
    protected String serialNumber;
    protected boolean competitionRobot;
    protected String name;
    protected boolean makeAllCANDevices;

    public RobotParametersBase() {
        serialNumber = "";
        competitionRobot = false;
        name = "";
        makeAllCANDevices = false;
    }

    @SuppressWarnings("unused")
    public String getSerialNumber() {
        return serialNumber;
    }

    public boolean isCompetitionRobot() {
        return competitionRobot;
    }

    public String getName() {
        return name;
    }

    // TODO, looks like Jackson is not serializing this field.
    public boolean shouldMakeAllCANDevices() {
        return makeAllCANDevices;
    }

    @Override
    public String toString() {
        return super.toString() + " [serial=" + serialNumber + ", competitionRobot=" + competitionRobot + ", name="
                + name + ", makeAllCANDevices=" + makeAllCANDevices + "]";
    }
}