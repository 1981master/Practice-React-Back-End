package com.master.practiceReact.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .findAndRegisterModules();

    private static final Set<String> SENSITIVE_FIELDS = Set.of("password", "token");

    // ==========================
    // Safe JSON serialization with filtering sensitive fields
    // ==========================
    private String safeSerialize(Object obj) {
        try {
            Object filtered = filterSensitive(obj);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filtered);
        } catch (Exception e) {
            return obj != null ? obj.toString() : "[No Object Exist]";
        }
    }
    @SuppressWarnings("unchecked")
    private Object filterSensitive(Object obj) {
        if (obj == null) return null;

        if (obj instanceof Map<?, ?> map) {
            Map<Object, Object> filteredMap = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key instanceof String keyStr && SENSITIVE_FIELDS.contains(keyStr.toLowerCase())) {
                    filteredMap.put(key, "[FILTERED]");
                } else {
                    filteredMap.put(key, filterSensitive(value)); // recursive
                }
            }
            return filteredMap;
        } else if (obj instanceof Collection<?> col) {
            List<Object> filteredList = new ArrayList<>();
            for (Object item : col) filteredList.add(filterSensitive(item));
            return filteredList;
        } else if (obj.getClass().isArray()) {
            int len = java.lang.reflect.Array.getLength(obj);
            List<Object> filteredList = new ArrayList<>();
            for (int i = 0; i < len; i++) filteredList.add(filterSensitive(java.lang.reflect.Array.get(obj, i)));
            return filteredList;
        } else if (obj instanceof Optional<?> opt) {
            return opt.map(this::filterSensitive).orElse(null);
        } else if (isPojo(obj)) {
            // INFO: Converting the POJO to Map via Jackson and filter recursively
            Map<String, Object> map = mapper.convertValue(obj, Map.class);
            return filterSensitive(map);
        }

        return obj;
    }

    private boolean isPojo(Object obj) {
        String pkg = obj.getClass().getPackageName();
        return !pkg.startsWith("java.") && !pkg.startsWith("jakarta.") && !obj.getClass().isEnum();
    }
    // ==========================
    // Service Layer
    // ==========================
    @Around("execution(* com.master.practiceReact.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("➡️ [Service] Entering: {} with args: {}", method, safeSerialize(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            logger.info("⬅️ [Service] Exiting: {} | Duration: {} ms | Result: {}", method, duration, safeSerialize(result));
            return result;
        } catch (Throwable t) {
            long duration = System.currentTimeMillis() - start;
            logger.error("❌ [Service] Exception in {} | Duration: {} ms | Message: {}", method, duration, t.getMessage(), t);
            throw t;
        }
    }

    // ==========================
    // Controller Layer
    // ==========================
    @Around("execution(* com.master.practiceReact.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("📦➡ [Controller] Entering: {} with args: {}", method, safeSerialize(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            logger.info("📦⬅ [Controller] Exiting: {} | Duration: {} ms | Result: {}", method, duration, safeSerialize(result));
            return result;
        } catch (Throwable t) {
            long duration = System.currentTimeMillis() - start;
            logger.error("❌ [Controller] Exception in {} | Duration: {} ms | Message: {}", method, duration, t.getMessage(), t);
            throw t;
        }
    }

    // ==========================
    // Repository Layer
    // ==========================
    @Around("execution(* com.master.practiceReact.repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("🗃️➡ [Repository] Entering: {} with args: {}", method, safeSerialize(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;

            logger.info("🗃️⬅ [Repository] Exiting: {} | Duration: {} ms | Result: {}", method, duration, safeSerialize(result));
            return result;
        } catch (Throwable t) {
            long duration = System.currentTimeMillis() - start;
            logger.error("❌ [Repository] Exception in {} | Duration: {} ms | Message: {}", method, duration, t.getMessage(), t);
            throw t;
        }
    }
}
