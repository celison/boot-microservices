import React, {useState} from 'react'
import makeStyles from "@material-ui/core/styles/makeStyles";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardActions from "@material-ui/core/CardActions";
import Typography from "@material-ui/core/Typography";
import {TextField} from "@material-ui/core";
import {Delete, Edit, Save} from "@material-ui/icons";
import IconButton from "@material-ui/core/IconButton";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";

const useStyles = makeStyles(theme => ({
    root: {
        height: '100%'
    }
}));

const getNameField = (isEditable, task, handleChange) => {
    return isEditable ? (
        <TextField
            fullWidth={true}
            value={task.name}
            onChange={(e) => {
                const taskCopy = {...task};
                taskCopy.name = e.target.value;
                handleChange(taskCopy);
            }}
        />
    ) : (
        <Typography>{task.name}</Typography>
    )
};

const getCompleteField = (isEditable, task, handleChange) => {
    return (
        <FormControlLabel
            disabled={!isEditable}
            control={
                <Checkbox checked={task.complete} onChange={(e) => {
                    const taskCopy = {...task};
                    taskCopy.complete = e.target.checked;
                    handleChange(taskCopy);
                }}
                />
            }
            label="Complete?"
        />
    )
};

const getCardContent = (isEditable, task, handleChange) => {
    return (
        <React.Fragment>
            {getNameField(isEditable, task, handleChange)}
            {getCompleteField(isEditable, task, handleChange)}
        </React.Fragment>
    )
};

const handleEditChange = (isEditable, setEditable, handleChange, task) => {
    return () => {
        if (isEditable) {
            handleChange(task)
        }
        setEditable(!isEditable);
    }
};

export const Task = (props) => {
    const [isEditable, setEditable] = useState(false);
    const {handleChange, removeTask} = props;
    const [task, setTask] = useState(props.task);
    const classes = useStyles();
    return (<Card className={classes.root}>
        <CardContent>
            {getCardContent(isEditable, task, setTask)}
        </CardContent>
        <CardActions>
            <IconButton size="small"
                        onClick={handleEditChange(isEditable, setEditable, handleChange, task)}>{isEditable ? <Save/> :
                <Edit/>}</IconButton>
            <IconButton size="small" onClick={() => {
                removeTask(task)
            }}><Delete/></IconButton>
        </CardActions>
    </Card>)
};