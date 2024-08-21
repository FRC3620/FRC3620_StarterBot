package org.usfirst.frc3620.logger;

import java.util.function.Supplier;

public interface IDataLogger {
    public void addPrelude(DataLoggerPrelude prelude);

    public void addPostlude(DataLoggerPostlude postlude);

    public void addDataProvider(String name, Supplier<Object> dataLoggerDataSupplier);

    public void addMetadata(String s, Object v);
}
