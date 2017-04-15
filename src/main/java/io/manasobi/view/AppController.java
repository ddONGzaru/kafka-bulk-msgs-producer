package io.manasobi.view;

import de.felixroske.jfxsupport.FXMLController;
import io.manasobi.config.KafkaConfig;
import io.manasobi.core.KafkaTaskManager;
import io.manasobi.view.log.LogbackLogAppender;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by tw.jang on 2017-04-13.
 */
@Slf4j
@FXMLController
public class AppController {

    @FXML
    private ChoiceBox msgTotalSize;

    @FXML
    private TextArea console;

    @FXML
    private Button execBtn;

    @FXML
    private TextField kafkaBrokerUrl;

    @FXML
    private TextField datasetDateTag;

    @FXML
    private TextField topic;

    @Autowired
    private KafkaTaskManager taskManager;

    private void setupKafkaConfig() {

        KafkaConfig.METADATA_BROKER_LIST = kafkaBrokerUrl.getText();

        KafkaConfig.MSG_TOTAL_SIZE = Integer.valueOf(msgTotalSize.getSelectionModel().getSelectedItem().toString().replaceAll(",", ""));

        KafkaConfig.DATASET_DATE_TAG = datasetDateTag.getText();

        KafkaConfig.TOPIC = topic.getText();
    }

    public void handleExecuteButtonAction() {

        console.clear();

        LogbackLogAppender.setTextArea(console);

        setupKafkaConfig();

        /*Alert startAlert = new Alert(Alert.AlertType.INFORMATION);
        startAlert.setTitle("Anypoint Kafka Producer ver-1.0.3");
        startAlert.setHeaderText("작업이 시작되었습니다.");
        startAlert.showAndWait();*/


        log.info("작업이 시작되었습니다.");



        taskManager.process(console);

        /*console.getParent().getScene().setCursor(Cursor.WAIT);
        Platform.runLater(() -> console.getParent().getScene().setCursor(Cursor.WAIT));*/

        /*

        boolean enableTruncateTableJob = isTruncateTable.isSelected();

        */



        //

        //Platform.runLater(() -> console.getParent().getScene().setCursor(Cursor.DEFAULT));
        //console.getParent().getScene().setCursor(Cursor.DEFAULT);

    }

    public void handleMouseEnteredAction() { execBtn.setCursor(Cursor.HAND); }

    public void handleMouseExitAction() {
        execBtn.setCursor(Cursor.DEFAULT);
    }

}
