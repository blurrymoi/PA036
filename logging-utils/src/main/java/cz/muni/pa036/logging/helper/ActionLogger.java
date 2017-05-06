package cz.muni.pa036.logging.helper;

import cz.muni.pa036.logging.utils.CRUDUtils;

import java.util.HashMap;
import java.util.Map;

public class ActionLogger {

    private String actionName;
    private Object causeObject;
    private Map<Object, Object> byValues;

    public ActionLogger(String actionName, Object causeObject, Map<Object, Object> byValues) {
        this.actionName = actionName;
        this.causeObject = causeObject;
        this.byValues = byValues;
    }

    @Override
    public String toString() {
        if (actionName.equalsIgnoreCase("find by")) {
            String params = CRUDUtils.prettyPrintMap(byValues);
            if (byValues.size() < 2) {  //only one param, remove []
                params = params.substring(1, params.length() - 1);
            } else {
                params = "params=".concat(params);
            }
            return actionName + " " + params;
        }
        return actionName + " " + causeObject;
    }

    public static final class ActionLoggerBuilder {
        private String actionName;
        private Object causeObject;
        private Map<Object, Object> byValues;

        public ActionLoggerBuilder() {
        }

        public ActionLoggerBuilder CREATE() {
            this.actionName = "create";
            return this;
        }

        public ActionLoggerBuilder FIND_BY() {
            this.actionName = "find by";
            return this;
        }

        public ActionLoggerBuilder FIND_BY_ID(Object id) {
            return new ActionLoggerBuilder().FIND_BY().byValue("ID", id);
        }

        public ActionLoggerBuilder UPDATE() {
            this.actionName = "update";
            return this;
        }

        public ActionLoggerBuilder DELETE() {
            this.actionName = "delete";
            return this;
        }

        public ActionLoggerBuilder CUSTOM(String actionName) {
            this.actionName = actionName;
            return this;
        }

        public ActionLoggerBuilder causeObject(Object causeObject) {
            this.causeObject = causeObject;
            return this;
        }

        public ActionLoggerBuilder byValue(Object by, Object byValue) {
            Map<Object, Object> byValues = new HashMap<>();
            byValues.put(by, byValue);
            this.byValues = byValues;
            return this;
        }

        public ActionLoggerBuilder byValues(Map<Object, Object> byValues) {
            this.byValues = byValues;
            return this;
        }

        public ActionLogger build() {
            ActionLogger actionLogger = new ActionLogger(actionName, causeObject, byValues);
            return actionLogger;
        }
    }
}
