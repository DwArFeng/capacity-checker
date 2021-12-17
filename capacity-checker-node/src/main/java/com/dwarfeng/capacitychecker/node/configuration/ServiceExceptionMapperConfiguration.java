package com.dwarfeng.capacitychecker.node.configuration;

import com.dwarfeng.capacitychecker.sdk.bean.util.ServiceExceptionCodes;
import com.dwarfeng.capacitychecker.stack.exception.*;
import com.dwarfeng.subgrade.impl.exception.MapServiceExceptionMapper;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceExceptionMapperConfiguration {

    @Bean
    public MapServiceExceptionMapper mapServiceExceptionMapper() {
        Map<Class<? extends Exception>, ServiceException.Code> destination = ServiceExceptionHelper.putDefaultDestination(null);
        destination.put(DriverException.class, ServiceExceptionCodes.DRIVER_FAILED);
        destination.put(UnsupportedDriverTypeException.class, ServiceExceptionCodes.DRIVER_TYPE_UNSUPPORTED);
        destination.put(CheckerException.class, ServiceExceptionCodes.CHECKER_FAILED);
        destination.put(CheckerMakeException.class, ServiceExceptionCodes.CHECKER_MAKE_FAILED);
        destination.put(UnsupportedCheckerTypeException.class, ServiceExceptionCodes.CHECKER_TYPE_UNSUPPORTED);
        destination.put(SectionNotExistsException.class, ServiceExceptionCodes.SECTION_NOT_EXISTS);
        destination.put(CapacityCheckOfflineException.class, ServiceExceptionCodes.CAPACITY_CHECK_OFFLINE);
        return new MapServiceExceptionMapper(destination, com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes.UNDEFINE);
    }
}
