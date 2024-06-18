package co.com.poli.users.helper;

import lombok.Builder;
import lombok.Data;

public class Response {
    private Integer code;
    private Object data;

    private Response(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
    }

    public static class Builder {
        private Integer code;
        private Object data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
