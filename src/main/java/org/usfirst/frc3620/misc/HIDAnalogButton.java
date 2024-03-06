package org.usfirst.frc3620.misc;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class HIDAnalogButton extends Trigger {
	public HIDAnalogButton(GenericHID hid, int axis) {
		this(hid, axis, 0.1);
	}

	public HIDAnalogButton(GenericHID hid, int axis, double threshold) {
		super(new HIDAnalogButtonBooleanSupplier(hid, axis, threshold));
	}

	static class HIDAnalogButtonBooleanSupplier implements BooleanSupplier {
		GenericHID hid;
		int axis;
		double threshold;

		public HIDAnalogButtonBooleanSupplier (GenericHID hid, int axis, double threshold) {
			this.hid = hid;
			this.axis = axis;
			this.threshold = threshold;
		}

		public boolean getAsBoolean() {
			return threshold > 0 ? hid.getRawAxis(axis) > threshold : hid.getRawAxis(axis) < threshold;
		}
	}
}