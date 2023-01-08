package com.clay.util;

import com.clay.exception.PermissionNotFoundException;
import com.clay.exception.UserNotFoundException;
import com.clay.model.Permission;
import com.clay.model.UserAction;
import com.clay.repo.PermissionRepository;
import com.clay.repo.UserActionRepository;
import com.clay.service.PermissionService;
import com.clay.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.clay.util.Constant.RESET;
import static com.clay.util.Constant.SET;

@Slf4j
@Service
public class BitConverter {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserActionRepository actionRepository;

    private Map<String, BiFunction<BigInteger, List<Integer>, BigInteger>> actions;

    @PostConstruct
    void init() {
        actions = new HashMap<>();
        actions.put(SET, bitMaskSetFunction);
        actions.put(RESET, bitMaskResetFunction);
    }

    public BigInteger buildUserActionBitmask(String action, Long permissionId, List<String> userActions) throws PermissionNotFoundException {
        com.clay.entity.Permission permissionFromDb = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException("Permission Not Found"));

        BigInteger defaultUserActionBitmask = permissionFromDb.getUserActionsBitmask();
        List<Integer> userActionOrdinals = actionRepository.getOrdinalByPermissionAndNameIn(permissionId, userActions);

        if (!userActionOrdinals.isEmpty()) {
            defaultUserActionBitmask = actions.get(action).apply(defaultUserActionBitmask, userActionOrdinals);
        }

        return defaultUserActionBitmask;
    }

    /**
     * method to capture Actions from bitmask
     */
    public List<String> buildUserActions(Long permissionId, BigInteger bitmaskValue) {
        List<Integer> ordinals = getOrdinals(bitmaskValue);

        return actionRepository.getActionNamesByPermissionIdAndOrdinals(permissionId, ordinals);
    }

    /**
     * Method to capture ordinals from bitmask
     */
    public List<Integer> getOrdinals(BigInteger actionBitMask) {
        String reversedBitmask = StringUtils.reverse(actionBitMask.toString(2));

        List<Integer> ordinals = new ArrayList<>();
        for (int i = 0; i < reversedBitmask.length(); i++) {
            if (reversedBitmask.charAt(i) == '1') {
                ordinals.add(i);
            }
        }
        return ordinals;
    }

    private final BiFunction<BigInteger, List<Integer>, BigInteger> bitMaskSetFunction = (defaultUserActionBitMask, ordinals) -> {
        defaultUserActionBitMask = defaultUserActionBitMask.clearBit(0);
        for (Integer ordinal : ordinals) {
            defaultUserActionBitMask = defaultUserActionBitMask.setBit(ordinal);
        }
        return defaultUserActionBitMask;
    };

    private final BiFunction<BigInteger, List<Integer>, BigInteger> bitMaskResetFunction = (defaultUserActionBitMask, ordinals) -> {
        for (Integer ordinal : ordinals) {
            defaultUserActionBitMask = defaultUserActionBitMask.clearBit(ordinal);
        }

        if (!defaultUserActionBitMask.toString(2).contains(BigInteger.ONE.toString())) {
            defaultUserActionBitMask = defaultUserActionBitMask.setBit(BigInteger.ZERO.intValue());
        }
        return defaultUserActionBitMask;
    };
}
