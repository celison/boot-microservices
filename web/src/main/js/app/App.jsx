import React, {useState} from 'react';
import {Header} from './nav/Header'
import {TaskMain} from "./task/TaskMain"
import {HomeMain} from "./home/HomeMain"
import Drawer from "@material-ui/core/Drawer";
import Divider from "@material-ui/core/Divider";
import {ListItemIcon, ListItemText} from "@material-ui/core";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {Event, Home, Menu} from "@material-ui/icons";
import {CalendarMain} from "./calendar/CalendarMain";
import {PollMain} from "./poll/PollMain"
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    useLocation,
} from "react-router-dom";

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
    },
    header: {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: drawerWidth,
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0
    },
    drawerPaper: {
        width: drawerWidth
    },
    toolbar: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
        padding: theme.spacing(3),
    }
}));

function ListItemLink(props) {
    const {icon, primary, to} = props;

    const renderLink = React.useMemo(
        () => React.forwardRef((itemProps, ref) => <Link to={to} ref={ref} {...itemProps} />),
        [to],
    );

    return (
        <li>
            <ListItem button component={renderLink}>
                {icon ? <ListItemIcon>{icon}</ListItemIcon> : null}
                <ListItemText primary={primary}/>
            </ListItem>
        </li>
    );
}

export const App = () => {
    const classes = useStyles();
    const location = useLocation();
    return (
        <div className={classes.root}>
            <Header
                className={classes.header}
                selectedPage={location.pathname}
            />
            <Drawer
                className={classes.drawer}
                classes={{
                    paper: classes.drawerPaper
                }}
                variant="permanent"
                anchor="left"
            >
                <div className={classes.toolbar}/>
                <Divider/>
                <List>
                    <ListItemLink to="/home" primary="Home" icon={<Home/>}/>
                    <ListItemLink to="/task" primary="Tasks" icon={<Menu/>}/>
                    <ListItemLink to="/calendar" primary="Calendar" icon={<Event/>}/>
                    <ListItemLink to="/poll" primary="Poll" icon={<Event/>}/>
                </List>
            </Drawer>
            <main className={classes.content}>
                <div className={classes.toolbar}/>
                <Switch>
                    <Route path="/home">
                        <HomeMain/>
                    </Route>
                    <Route path="/task">
                        <TaskMain/>
                    </Route>
                    <Route path="/calendar">
                        <CalendarMain/>
                    </Route>
                    <Route path="/poll">
                        <PollMain/>
                    </Route>
                </Switch>
            </main>
        </div>
    )
};