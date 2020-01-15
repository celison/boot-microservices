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

const renderPage = (page) => {
    switch (page.toLowerCase()) {
        case 'home':
            return <HomeMain/>;
        case 'task':
            return <TaskMain/>;
        case 'calendar':
            return <CalendarMain/>;
    }
};

export const App = () => {
    const [selectedPage, setSelectedPage] = useState("home");
    const classes = useStyles();

    return (
    <div className={classes.root}>
        <Header
            className={classes.header}
            selectedPage={selectedPage}
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
                <ListItem button onClick={() => setSelectedPage('home')}>
                    <ListItemIcon><Home/></ListItemIcon>
                    <ListItemText>Home</ListItemText>
                </ListItem>
                <ListItem button onClick={() => setSelectedPage('task')}>
                    <ListItemIcon><Menu/></ListItemIcon>
                    <ListItemText>Tasks</ListItemText>
                </ListItem>
                <ListItem button onClick={() => setSelectedPage('calendar')}>
                    <ListItemIcon><Event/></ListItemIcon>
                    <ListItemText>Calendar</ListItemText>
                </ListItem>
            </List>
        </Drawer>
        <main className={classes.content}>
            <div className={classes.toolbar}/>
            {renderPage(selectedPage)}
        </main>
    </div>
)};