import React, {useEffect, useState} from 'react'
import {GridList, GridListTile} from "@material-ui/core";
import {Task} from "./Task";
import {deleteTask, getTasks, postTask, putTask} from "./TaskApi";
import {Add} from "@material-ui/icons";
import Typography from "@material-ui/core/Typography";
import Fab from "@material-ui/core/Fab";
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles((theme) => ({
    button: {
        float: 'right',
    }
}));

const updateTask = (setTasks) => {
    return (task) => {
        putTask(task).then(() => refreshTasks(setTasks));
    }
};

const removeTask = (setTasks) => {
    return (task) => {
        deleteTask(task).then(() => refreshTasks(setTasks))
    }
};

const getDefaultNewTask = () => {
    return {
        name: "New Task",
    }
};

const createTask = (setTasks) => {
    return () => {
        postTask(getDefaultNewTask()).then(() => refreshTasks(setTasks));
    }
};

const refreshTasks = (setTasks) => {
    getTasks().then(res => setTasks(res));
};

export const TaskMain = () => {
    const [tasks, setTasks] = useState([]);
    const classes = useStyles();
    useEffect(() => {
        refreshTasks(setTasks);
    }, []);
    return (
        <React.Fragment>
            <Typography display="inline" variant="h2">
                Tasks
                <Fab className={classes.button} color="primary" onClick={createTask(setTasks)}>
                    <Add/>
                </Fab>
            </Typography>
            <GridList cols={4}>
                {tasks.map(task => (
                    <GridListTile key={task.id}>
                        <Task
                            task={task}
                            handleChange={updateTask(setTasks)}
                            removeTask={removeTask(setTasks)}
                        />
                    </GridListTile>
                ))}
            </GridList>
        </React.Fragment>
    )
};