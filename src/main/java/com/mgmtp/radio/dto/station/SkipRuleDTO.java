package com.mgmtp.radio.dto.station;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@NoArgsConstructor
@Data
public class SkipRuleDTO {

	public static final int BASIC = 0;
	public static final int ADVANCE = 1;

	int typeId;

    public SkipRuleDTO() {
    	this.typeId = BASIC;
    }

    @Setter(AccessLevel.NONE)
    String name;
	@Setter(AccessLevel.NONE)
    String description;

    public String getName() {
    	switch (typeId) {
		    case BASIC:
		    	name = "Basic rule";
		    	break;
		    case ADVANCE:
		    	name = "Advance rule";
			    break;
		    default:
		    	name = "Invalid typeId";
			    break;
	    }
	    return name;
    }

	public String getDescription() {
		switch (typeId) {
			case BASIC:
				description = "Rule: More than 50% downvotes can skip the song";
				break;
			case ADVANCE:
				description = "Rule: Only you can skip the song";
				break;
			default:
				description = "Invalid typeId";
				break;
		}
		return description;
	}

	public SkipRuleDTO(int typeId) throws InvalidRuleTypeDtoException {
		if(typeId != BASIC || typeId == ADVANCE) {
			this.typeId = typeId;
		}
		else {
			throw new InvalidRuleTypeDtoException();
		}
	}

	public class InvalidRuleTypeDtoException extends Exception {
		@Override
		public String getMessage() {
			return "The input RuleTypeDTO is invalid or empty";
		}
	}
}
