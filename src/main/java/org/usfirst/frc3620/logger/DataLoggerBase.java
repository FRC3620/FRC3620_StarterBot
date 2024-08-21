package org.usfirst.frc3620.logger;

import java.util.*;
import java.util.function.Supplier;

import org.apache.logging.log4j.Logger;
import org.usfirst.frc3620.logger.EventLogging.FRC3620Level;

abstract public class DataLoggerBase implements IDataLogger {
	List<NamedDataSupplier> namedDataProviders = new ArrayList<>();

	List<DataLoggerPrelude> preludes = new ArrayList<>();
	List<DataLoggerPostlude> postludes = new ArrayList<>();

	Logger logger = EventLogging.getLogger(getClass(), FRC3620Level.INFO);

	Timer timer;

	@Override
	public void addDataProvider(String _name, Supplier<Object> dataLoggerDataSupplier) {
		namedDataProviders.add(new NamedDataSupplier(_name, dataLoggerDataSupplier));
	}

	@Override
	public void addPrelude(DataLoggerPrelude prelude) {
		preludes.add(prelude);
	}

	@Override
	public void addPostlude(DataLoggerPostlude postlude) {
		postludes.add(postlude);
	}

	@Override
	abstract public void addMetadata(String s, Object metadata);

	public void setup() {
	}

	public void runCycle() {
		runCyclePrelude();
		for (var prelude : preludes) {
			prelude.dataLoggerPrelude();
		}

		int i = 0;
		for (NamedDataSupplier namedDataProvider : namedDataProviders) {
			try {
				saveValue(i, namedDataProvider.name, namedDataProvider.dataLoggerDataSupplier.get());
			} catch (Exception e) {
				saveErrorValue(i, namedDataProvider.name, e);
			}
			i++;
		}

		for (var postlude : postludes) {
			postlude.dataLoggerPostlude();
		}
		runCyclePrelude();
	}

	abstract void saveValue(int i, String name, Object value);

	abstract void saveErrorValue(int i, String name, Exception e);

	public void runCyclePrelude() {
	}

	public void runCyclePostlude() {
	}

	public class NamedDataSupplier {
		public NamedDataSupplier(String name, Supplier<Object> dataLoggerDataSupplier) {
			super();
			this.name = name;
			this.dataLoggerDataSupplier = dataLoggerDataSupplier;
		}

		String name;
		Supplier<Object> dataLoggerDataSupplier;
	}
}
