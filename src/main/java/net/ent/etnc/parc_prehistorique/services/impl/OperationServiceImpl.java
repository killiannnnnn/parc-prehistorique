package net.ent.etnc.parc_prehistorique.services.impl;

import net.ent.etnc.parc_prehistorique.models.entities.Operation;
import net.ent.etnc.parc_prehistorique.repositories.OperationRepository;
import net.ent.etnc.parc_prehistorique.services.OperationService;
import net.ent.etnc.parc_prehistorique.services.commons.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl extends AbstractService<Operation, OperationRepository> implements OperationService {

    @Autowired
    public OperationServiceImpl(OperationRepository repository) {
        super(repository);
    }
}