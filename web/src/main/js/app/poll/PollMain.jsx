import React, {useEffect, useState} from "react";
import Typography from "@material-ui/core/Typography";
import {GridList, GridListTile} from "@material-ui/core";
import {getQuestions} from "./PollApi";


const refreshTasks = (setTasks) => {
    getQuestions().then(res => setTasks(res));
};

export const PollMain = () => {
    const [questions, setQuestions] = useState([]);
    // const classes = useStyles();
    useEffect(() => {
        refreshTasks(setQuestions);
    }, []);
    return (
        <React.Fragment>
            <Typography display="inline" variant="h2">
                Questions
                {/*<Fab className={classes.button} color="primary" onClick={createTask(setTasks)}>*/}
                {/*    <Add/>*/}
                {/*</Fab>*/}
            </Typography>
            <GridList cols={4}>
                {questions.map(question => (
                    <GridListTile key={question.id}>
                        <h1>{question.questionText}</h1>
                    </GridListTile>
                ))}
            </GridList>
        </React.Fragment>
    )
};