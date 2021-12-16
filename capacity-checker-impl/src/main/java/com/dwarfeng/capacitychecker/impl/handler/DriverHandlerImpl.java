package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.exception.UnsupportedDriverTypeException;
import com.dwarfeng.capacitychecker.stack.handler.Driver;
import com.dwarfeng.capacitychecker.stack.handler.DriverHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DriverHandlerImpl implements DriverHandler {

    @SuppressWarnings("FieldMayBeFinal")
    private final List<DriverProvider> driverProviders;

    public DriverHandlerImpl(
            @Autowired List<DriverProvider> driverProviders
    ) {
        this.driverProviders = Objects.isNull(driverProviders) ? new ArrayList<>() : driverProviders;
    }

    @Override
    public Driver find(String type) throws HandlerException {
        return driverProviders.stream().filter(dp -> dp.supportType(type)).map(DriverProvider::provide)
                .findAny().orElseThrow(() -> new UnsupportedDriverTypeException(type));
    }
}
