package alavadeiraapp.com.example.maiconh.alavadeiraapp.model;

import java.util.List;

/**
 * Created by vinicius on 13/02/17.
 */

public class User {
    private String message;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;
        private ProfileBean profile;
        private DriverBean driver;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public ProfileBean getProfile() {
            return profile;
        }

        public void setProfile(ProfileBean profile) {
            this.profile = profile;
        }

        public DriverBean getDriver() {
            return driver;
        }

        public void setDriver(DriverBean driver) {
            this.driver = driver;
        }

        public static class ProfileBean {
            private String email;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        public static class DriverBean {

            private String car_plate;
            private int id;
            private String name;
            private List<String> trajectories;

            public String getCar_plate() {
                return car_plate;
            }

            public void setCar_plate(String car_plate) {
                this.car_plate = car_plate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getTrajectories() {
                return trajectories;
            }

            public void setTrajectories(List<String> trajectories) {
                this.trajectories = trajectories;
            }
        }
    }
}
