package org.example.demo.controllers.utils;

import lombok.Getter;
import org.example.demo.data.repositories.database.*;
import org.example.demo.data.repositories.interfaces.*;
import org.example.demo.services.*;
import org.example.demo.services.interfaces.*;

@Getter

public class Factory {
    private final IServiceAccount serviceAccount;
    private final IServiceDebt serviceDebt;
    private final IServiceDetail serviceDetail;
    private final IServicePayment servicePayment;
    private final IServiceClient serviceClient;
    private final IServiceArticle serviceArticle;

    public Factory() {
        IRepositoryAccount repositoryAccount = new RepositoryDbAccount();
        serviceAccount = new ServiceAccount(repositoryAccount);
        IRepositoryDebt repositoryDebt = new RepositoryDbDebt();
        serviceDebt = new ServiceDebt(repositoryDebt);
        IRepositoryDetail repositoryDetail = new RepositoryDbDetail();
        serviceDetail = new ServiceDetail(repositoryDetail);
        IRepositoryPayment repositoryPayment = new RepositoryDbPayment();
        servicePayment = new ServicePayment(repositoryPayment);
        IRepositoryClient repositoryClient = new RepositoryDbClient();
        serviceClient = new ServiceClient(repositoryClient);
        IRepositoryArticle repositoryArticle = new RepositoryDbArticle();
        serviceArticle = new ServiceArticle(repositoryArticle);
    }


}
