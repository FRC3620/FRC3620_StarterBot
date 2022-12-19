package org.usfirst.frc3620.misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Container for RobotParameters; designed to be subclassed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RobotParameters {
    protected String macAddress;
    protected boolean competitionRobot;
    protected String name;

    public RobotParameters() {
        macAddress = "";
        competitionRobot = false;
        name = "";
    }

    public String getMacAddress() {
        return macAddress;
    }

    public boolean isCompetitionRobot() {
        return competitionRobot;
    }

    public String getName() {
        return name;
    }
}