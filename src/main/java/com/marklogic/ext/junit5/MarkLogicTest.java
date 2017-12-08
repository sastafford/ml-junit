package com.marklogic.ext.junit5;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.datamovement.DataMovementManager;
import com.marklogic.client.datamovement.DeleteListener;
import com.marklogic.client.datamovement.QueryBatcher;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class MarkLogicTest implements BeforeTestExecutionCallback {

    private static Logger logger = LoggerFactory.getLogger(MarkLogicTest.class);

    private DatabaseClient databaseClient;
    private DataMovementManager dmm;
    private StructuredQueryDefinition query;

    private String host;
    private int port;
    private String username;
    private String password;


    public MarkLogicTest() throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("test.properties"));
        host = props.getProperty("marklogic.test.host", "localhost");
        port = new Integer(props.getProperty("marklogic.test.port", "8000")).intValue();
        username = props.getProperty("marklogic.test.username", "admin");
        password = props.getProperty("marklogic.test.password", "admin");

        databaseClient = DatabaseClientFactory.newClient(host, port,
                new DatabaseClientFactory.DigestAuthContext(username, password));
        dmm = databaseClient.newDataMovementManager();
        QueryManager qm = databaseClient.newQueryManager();
        StructuredQueryBuilder sqb = qm.newStructuredQueryBuilder();
        query = sqb.and();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        logger.debug("Deleting content from database");
        QueryBatcher batcher = dmm.newQueryBatcher(query);
        batcher.withConsistentSnapshot()
                .onUrisReady(new DeleteListener())
                .withThreadCount(2)
                .withBatchSize(100);
        //.onQueryFailure( exception -> exception.printStackTrace() );
        dmm.startJob(batcher);
        batcher.awaitCompletion();
        dmm.stopJob(batcher);
    }
}
