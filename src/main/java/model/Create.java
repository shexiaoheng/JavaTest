package model;

public class Create {
    private String node_code;
    private String project_name;
    private String service_id;
    private String subscribe_status;
    private String team_id;
    private String api_cluster_name;
    private AuthInfo auth_info;
    private PaymentInfo payment_info;

    public String getNode_code() {
        return node_code;
    }

    public void setNode_code(String node_code) {
        this.node_code = node_code;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getSubscribe_status() {
        return subscribe_status;
    }

    public void setSubscribe_status(String subscribe_status) {
        this.subscribe_status = subscribe_status;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getApi_cluster_name() {
        return api_cluster_name;
    }

    public void setApi_cluster_name(String api_cluster_name) {
        this.api_cluster_name = api_cluster_name;
    }

    public AuthInfo getAuth_info() {
        return auth_info;
    }

    public void setAuth_info(AuthInfo auth_info) {
        this.auth_info = auth_info;
    }

    public PaymentInfo getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(PaymentInfo payment_info) {
        this.payment_info = payment_info;
    }
}
