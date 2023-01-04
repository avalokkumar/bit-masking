package com.clay.util;

import com.clay.exception.PermissionNotFoundException;
import com.clay.exception.UserNotFoundException;
import com.clay.model.Permission;
import com.clay.model.UserAction;
import com.clay.repo.PermissionRepository;
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
    private UserService userService;

    @Autowired
    private PermissionRepository permissionRepository;

    private Map<String, BiFunction<BigInteger, List<Integer>, BigInteger>> actions;

    @PostConstruct
    void init() {
        actions = new HashMap<>();
        actions.put(SET, bitMaskSetFunction);
        actions.put(RESET, bitMaskResetFunction);
    }

    public BigInteger buildUserActionBitmask(List<UserAction> userActions) {
  /*      List<Permission> permissions = userService.getUserById(userId).getPermissions();
        Map<Integer, List<Integer>> permissionActionMapping = permissions.stream() //permission - action mapping
                .collect(
                        Collectors.toMap(
                                Permission::getId,
                                permission -> permission.getUserActions().stream().map(Enum::ordinal)
                                        .collect(Collectors.toList())
                        )
                );
*/
        /*Map<BigInteger, List<Integer>> defaultUsageBitMaskAndOrdinalMapping = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> permissionEntry : permissionActionMapping.entrySet()) {

            try {
                com.clay.entity.Permission permission = permissionRepository.findById(permissionEntry.getKey())
                        .orElseThrow(() -> new PermissionNotFoundException("Permission Not Found"));

                defaultUsageBitMaskAndOrdinalMapping.put(permission.getUserActionsBitmask(), permissionEntry.getValue());

            } catch (PermissionNotFoundException e) {
                log.error("Permission Not Found");
                return null;
            }
        }*/

        /*if (!defaultUsageBitMaskAndOrdinalMapping.isEmpty()) {
            defaultUsageBitMaskAndOrdinalMapping.entrySet()
                    .stream()
                    .map(
                            entry -> entry.getValue().stream()
                                    .map(value -> actions.get(bitmaskAction).apply(defaultUsageBitMask, ordinals))
                    )
                    .collect(Collectors.toMap(entry -> entry.));

            defaultUsageBitMask = actions.get(bitmaskAction).apply(defaultUsageBitMask, ordinals);
        }*/

        return null;
    }

    public List<UserAction> buildUserActions(BigInteger bitmaskValue) {

        return Collections.emptyList();
    }

    /**
     * method to extract usage reason from bitmask
     */
  /*  public List<String> buildUsageReasons(BigInteger usageReasonsBitMask) {
        List<Integer> ordinals = getOrdinals(usageReasonsBitMask);

        return userGroupService.getAllReasonsByOrdinals(ordinals);
    }*/


    /**
     * Method to extract ordinals from bitmask
     */
    public List<Integer> getOrdinals(BigInteger usageReasonsBitMask) {
        String reversedBitmask = StringUtils.reverse(usageReasonsBitMask.toString(2));

        List<Integer> ordinals = new ArrayList<>();
        for (int i = 0; i < reversedBitmask.length(); i++) {
            if (reversedBitmask.charAt(i) == '1') {
                ordinals.add(i);
            }
        }
        return ordinals;
    }

    private BiFunction<BigInteger, List<Integer>, BigInteger> bitMaskSetFunction = (defaultUsageBitMask, ordinals) -> {
        defaultUsageBitMask = defaultUsageBitMask.clearBit(0);
        for (Integer ordinal : ordinals) {
            defaultUsageBitMask = defaultUsageBitMask.setBit(ordinal);
        }
        return defaultUsageBitMask;
    };

    private BiFunction<BigInteger, List<Integer>, BigInteger> bitMaskResetFunction = (defaultUsageBitMask, ordinals) -> {
        for (Integer ordinal : ordinals) {
            defaultUsageBitMask = defaultUsageBitMask.clearBit(ordinal);
        }

        if (!defaultUsageBitMask.toString(2).contains(BigInteger.ONE.toString())) {
            defaultUsageBitMask = defaultUsageBitMask.setBit(BigInteger.ZERO.intValue());
        }
        return defaultUsageBitMask;
    };
}
