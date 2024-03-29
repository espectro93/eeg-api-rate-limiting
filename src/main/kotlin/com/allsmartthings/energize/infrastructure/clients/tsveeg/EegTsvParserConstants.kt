package com.allsmartthings.energize.infrastructure.clients.tsveeg

object EegV1Constants {
    const val YEAR_MONTH = 0
    const val BELOW_THIRTY_KWP = 1
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP = 2
    const val ABOVE_HUNDRED_BELOW_THOUSAND_KWP = 3
    const val ABOVE_THOUSAND_KWP = 4
    const val OPEN_SPACE_SYSTEM = 5
    const val FACADE_ENCLOSURE = 6
}

data class EegV1Vars(
        val date: String,
        val belowThirtyKwp: String,
        val aboveThirtyBelowHundred: String,
        val aboveHundredBelowThousand: String,
        val aboveThousand: String,
        val openSpaceSystem: String,
        val facadeEnclosure: String
) {
    companion object {
        fun parseFromRawInput(rawInput: String): EegV1Vars {
            val tokens = rawInput.split("\t");
            return EegV1Vars(
                    tokens[EegV1Constants.YEAR_MONTH],
                    tokens[EegV1Constants.BELOW_THIRTY_KWP],
                    tokens[EegV1Constants.ABOVE_THIRTY_BELOW_HUNDRED_KWP],
                    tokens[EegV1Constants.ABOVE_HUNDRED_BELOW_THOUSAND_KWP],
                    tokens[EegV1Constants.ABOVE_THOUSAND_KWP],
                    tokens[EegV1Constants.OPEN_SPACE_SYSTEM],
                    tokens[EegV1Constants.FACADE_ENCLOSURE]
            )
        }
    }
}

object EegV2Constants {
    const val YEAR_MONTH = 0
    const val BELOW_THIRTY_KWP = 1
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP = 2
    const val ABOVE_HUNDRED_BELOW_THOUSAND_KWP = 3
    const val ABOVE_THOUSAND_KWP = 4
    const val BELOW_THIRTY_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT = 5
    const val BELOW_THIRTY_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT = 6
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT = 7
    const val ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT = 8
    const val ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT = 9
    const val ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT = 10
    const val SYSTEM_ON_SEALED_OR_CONVERSION_AREA = 11
    const val OPEN_SPACE_SYSTEM = 12
    const val SYSTEM_ON_ACRE = 13
}

data class EegV2Vars(
        val date: String,
        val belowThirtyKwp: String,
        val aboveThirtyBelowHundred: String,
        val aboveHundredBelowThousand: String,
        val aboveThousand: String,
        val openSpaceSystem: String,
        val belowThirtyKwpSelfConsumptionBelowThreshold: String,
        val belowThirtyKwpSelfConsumptionAboveThreshold: String,
        val aboveThirtyBelowHundredKwpSelfConsumptionBelowThreshold: String,
        val aboveThirtyBelowHundredKwpSelfConsumptionAboveThreshold: String,
        val aboveHundredBelowFiveHundredKwpSelfConsumptionBelowThreshold: String,
        val aboveHundredBelowFiveHundredKwpSelfConsumptionAboveThreshold: String,
        val systemOnSealedOrConversionArea: String,
        val systemOnAcre: String,
) {
    companion object {
        fun parseFromRawInput(rawInput: String): EegV2Vars {
            val tokens = rawInput.split("\t");
            return EegV2Vars(
                    tokens[EegV2Constants.YEAR_MONTH],
                    tokens[EegV2Constants.BELOW_THIRTY_KWP],
                    tokens[EegV2Constants.ABOVE_THIRTY_BELOW_HUNDRED_KWP],
                    tokens[EegV2Constants.ABOVE_HUNDRED_BELOW_THOUSAND_KWP],
                    tokens[EegV2Constants.ABOVE_THOUSAND_KWP],
                    tokens[EegV2Constants.OPEN_SPACE_SYSTEM],
                    tokens[EegV2Constants.BELOW_THIRTY_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT],
                    tokens[EegV2Constants.BELOW_THIRTY_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT],
                    tokens[EegV2Constants.ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT],
                    tokens[EegV2Constants.ABOVE_THIRTY_BELOW_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT],
                    tokens[EegV2Constants.ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_TILL_THIRTY_PERCENT],
                    tokens[EegV2Constants.ABOVE_HUNDRED_BELOW_FIVE_HUNDRED_KWP_SELF_CONSUMPTION_ABOVE_THIRTY_PERCENT],
                    tokens[EegV2Constants.SYSTEM_ON_SEALED_OR_CONVERSION_AREA],
                    tokens[EegV2Constants.SYSTEM_ON_ACRE]
            )
        }
    }
}

object EegV3Constants {
    const val YEAR_MONTH = 0
    const val BELOW_TEN_KWP = 1
    const val ABOVE_TEN_BELOW_FORTY_KWP = 2
    const val ABOVE_FORTY_BELOW_THOUSAND_KWP = 3
    const val ABOVE_THOUSAND_BELOW_TEN_THOUSAND_KWP = 4
    const val NOT_RESIDENTIAL_BUILDING_EXTERIOR = 5
    const val SYSTEM_ON_SEALED_OR_CONVERSION_AREA = 6
}

data class EegV3Vars(
        val date: String,
        val belowTenKwp: String,
        val aboveTenBelowFortyKwp: String,
        val aboveFortyBelowThousandKwp: String,
        val aboveThousandBelowTenThousandKwp: String,
        val notResidentialBuildingExterior: String,
        val systemOnSealedOrConversionArea: String
) {
    companion object {
        fun parseFromRawInput(rawInput: String): EegV3Vars {
            val tokens = rawInput.split("\t");
            return EegV3Vars(
                    tokens[EegV3Constants.YEAR_MONTH],
                    tokens[EegV3Constants.BELOW_TEN_KWP],
                    tokens[EegV3Constants.ABOVE_TEN_BELOW_FORTY_KWP],
                    tokens[EegV3Constants.ABOVE_FORTY_BELOW_THOUSAND_KWP],
                    tokens[EegV3Constants.ABOVE_THOUSAND_BELOW_TEN_THOUSAND_KWP],
                    tokens[EegV3Constants.NOT_RESIDENTIAL_BUILDING_EXTERIOR],
                    tokens[EegV3Constants.SYSTEM_ON_SEALED_OR_CONVERSION_AREA]
            )
        }
    }
}

object EegV4Constants {
    const val YEAR_MONTH = 0
    const val BELOW_TEN_KWP = 1
    const val ABOVE_TEN_BELOW_FORTY_KWP = 2
    const val ABOVE_FORTY_BELOW_FIVE_HUNDRED_KWP = 3
    const val NOT_RESIDENTIAL_BUILDING_EXTERIOR = 4
    const val SYSTEM_ON_SEALED_OR_CONVERSION_AREA = 5
}

data class EegV4Vars(
        val date: String,
        val belowTenKwp: String,
        val aboveTenBelowFortyKwp: String,
        val aboveFortyBelowFiveHundredKwp: String,
        val notResidentialBuildingExterior: String,
        val systemOnSealedOrConversionArea: String
) {
    companion object {
        fun parseFromRawInput(rawInput: String): EegV4Vars {
            val tokens = rawInput.split("\t");
            return EegV4Vars(
                    tokens[EegV4Constants.YEAR_MONTH],
                    tokens[EegV4Constants.BELOW_TEN_KWP],
                    tokens[EegV4Constants.ABOVE_TEN_BELOW_FORTY_KWP],
                    tokens[EegV4Constants.ABOVE_FORTY_BELOW_FIVE_HUNDRED_KWP],
                    tokens[EegV4Constants.NOT_RESIDENTIAL_BUILDING_EXTERIOR],
                    tokens[EegV4Constants.SYSTEM_ON_SEALED_OR_CONVERSION_AREA]
            )
        }
    }
}

object EegV5Constants {
    const val YEAR_MONTH = 0
    const val BELOW_TEN_KWP = 1
    const val ABOVE_TEN_BELOW_FORTY_KWP = 2
    const val ABOVE_FORTY_BELOW_HUNDRED_KWP = 3
    const val NOT_RESIDENTIAL_BUILDING_EXTERIOR = 4
}

data class EegV5Vars(
        val date: String,
        val belowTenKwp: String,
        val aboveTenBelowFortyKwp: String,
        val aboveFortyBelowFiveHundredKwp: String,
        val notResidentialBuildingExterior: String,
) {
    companion object {
        fun parseFromRawInput(rawInput: String): EegV5Vars {
            val tokens = rawInput.split("\t");
            return EegV5Vars(
                    tokens[EegV5Constants.YEAR_MONTH],
                    tokens[EegV5Constants.BELOW_TEN_KWP],
                    tokens[EegV5Constants.ABOVE_TEN_BELOW_FORTY_KWP],
                    tokens[EegV5Constants.ABOVE_FORTY_BELOW_HUNDRED_KWP],
                    tokens[EegV5Constants.NOT_RESIDENTIAL_BUILDING_EXTERIOR]
            )
        }
    }
}