package password.manager.Controller;

import password.manager.Model.Service;
import password.manager.Model.ServiceListModel;

public class ServiceListController {
    private Controller controller;
    private ServiceListModel serviceList;
    public ServiceListController(Controller controller) {
        this.controller = controller;
        this.serviceList = new ServiceListModel(this);
    }
    
    public void addNew() {
        while (true) {
            String newServiceName = controller.getView().promptNewServiceName();
            
            if (newServiceName == null) {
                break;
            }
            
            Service freshService = new Service();
            freshService.setServiceName(newServiceName);
            
            if (newServiceName.length() < 1) {
                controller.getView().errorMessage("Service name cannot be empty!");
            }
            else if (!serviceList.addService(freshService)) {
                controller.getView().errorMessage("Service already exists!");
            }
            else {
                updateList();
                break;
            }
        } 
    }
    
    public void removeCurrent() {
        String[] serviceNames = controller.getView().getSelectedServices();
        
        if (serviceNames.length > 0) {
            String message;
            
            if (serviceNames.length == 1) {
                message = String.format("Are you sure you want to delete %s?", serviceNames[0]);
            }
            else {
                message = String.format("Are you sure you want to delete these %d services?", serviceNames.length);
            }
            
            boolean delete = controller.getView().promptYesNo("Delete?", message);
            
            if (delete) {
                for (String serviceName : serviceNames) {
                    serviceList.removeService(serviceName);
                }
                updateList();
            }
        }
    }
    
    public void updateList() {
        String[] serviceNames = serviceList.getServiceNames();
        controller.getView().setServiceList(serviceNames);
    }
}