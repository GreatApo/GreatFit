package es.LBA97.PSourceInverted.data;

/**
 * Data types
 */

public enum DataType {

    STEPS(1) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new Steps((int) args[0], (int) args[1]);
        }
    },
    DISTANCE(2) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new TodayDistance((double) args[0]);
        }
    },
    TOTAL_DISTANCE(3) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new TotalDistance((double) args[0]);
        }
    },
    CALORIES(4) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new Calories((float) args[0]);
        }
    },
    HEART_RATE(5) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new HeartRate((int) args[0]);
        }
    },
    DATE(6) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new Date((int) args[0], (int) args[1], (int) args[2], (int) args[3]);
        }
    },
    TIME(7) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new Time((int) args[0], (int) args[1], (int) args[2], (int) args[3]);
        }
    },

    BATTERY(10) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new Battery((int) args[0], (int) args[1]);
        }
    },

    FLOOR(12) {
        @Override
        protected <E> E getValue(Object... args){
            return (E) new TodayFloor ((int) args[0]);
        }

    },

    AMPM(13) {
        @Override
        protected <E> E getValue(Object... args){
            return (E) new AmPm ();
        }
    },

    WEATHER(8) {
        @Override
        protected <E> E getValue(Object... args) {
            return (E) new WeatherData((String) args[0], (String) args[1], ((Integer) args[2]).intValue());
        }
    };

    private final int dataType;

    private DataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        return this.dataType;
    }

    protected abstract <E> E getValue(Object... args);

    public static final DataType fromValue(int i) {
        for (DataType type : values()) {
            if (type.dataType == i) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("Type %s not found", i));
    }
}
