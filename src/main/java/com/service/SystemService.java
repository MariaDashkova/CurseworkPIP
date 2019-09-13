package com.service;

import com.database.SystemEntity;
import com.database.TransactionEntity;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    private final SystemRepository systemRepository;
    private final TransactionRepository transactionRepository;
    private final StudioRpository studioRpository;
    private final AnalystRepository analystRepository;

    @Autowired
    public SystemService(SystemRepository systemRepository, TransactionRepository transactionRepository,
                         StudioRpository studioRpository, AnalystRepository analystRepository) {
        this.systemRepository = systemRepository;
        this.transactionRepository = transactionRepository;
        this.studioRpository = studioRpository;
        this.analystRepository = analystRepository;

    }

    public String returnInfo() {
        int id_sysrem = 1;
        try {
            SystemEntity systemEntity = systemRepository.findById(id_sysrem);
            return systemEntity.getName() + "\n" + systemEntity.getAdminMail()
                    + "\n" + systemEntity.getBody();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public String moneyTransfer(long money, int id_studio, int id_analyst) {
        try {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setIdRecipient(id_analyst);
            transactionEntity.setIdSender(id_studio);
            transactionEntity.setSum(money);
            studioRpository.updatePurse(studioRpository.findByIdStudio(id_studio).getPurse() - money, id_studio);
            analystRepository.updatePurse(analystRepository.findByIdAnalyst(id_analyst).getPurse() + money, id_analyst);
            transactionRepository.save(transactionEntity);
            return "Successful done";
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
