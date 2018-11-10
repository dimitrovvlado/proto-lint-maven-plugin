package io.github.dimitrovvlado.proto.lint.validation;

import com.squareup.wire.schema.internal.parser.ProtoFileElement;
import com.squareup.wire.schema.internal.parser.RpcElement;
import com.squareup.wire.schema.internal.parser.ServiceElement;

import java.util.LinkedList;
import java.util.List;

import static io.github.dimitrovvlado.proto.lint.validation.CaseFormat.getCase;

public class ServicesValidator implements Validator  {

    @Override
    public List<ValidationResult> validate(ProtoFileElement protoFile) {
        List<ValidationResult> result = new LinkedList<>();
        for (ServiceElement serviceElement : protoFile.services()) {
            if (!CaseFormat.UPPER_CAMEL.equals(getCase(serviceElement.name()))) {
                result.add(new ValidationResult(ValidationResult.Severity.ERROR,
                        String.format("Service name '%s' is not in upper camel case.", serviceElement.name())));
            }
            for (RpcElement rpc : serviceElement.rpcs()) {
                if (!CaseFormat.UPPER_CAMEL.equals(getCase(rpc.name()))) {
                    result.add(new ValidationResult(ValidationResult.Severity.ERROR,
                            String.format("RPC name '%s' in service '%s' is not in upper camel case.", serviceElement.name(), rpc.name())));
                }
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "servicesValidator";
    }
}
