import React from 'react'
import {AppBar, Toolbar, Typography, makeStyles} from '@material-ui/core'

const useStyles = makeStyles((theme) => ({
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));

const getHeaderForSelectedPage = (selectedPage) => {
    switch (selectedPage) {
        case '/home':
            return 'Home Page';
        case '/task':
            return 'Tasks';
        case '/calendar':
            return 'Calendar';
        case '/poll':
            return 'Poll';
    }
};

export const Header = (props) => {
    const classes = useStyles();

    return (
        <AppBar
            className={props.className}
            position="fixed"
        >
            <Toolbar>
                <Typography className={classes.title}>
                    {getHeaderForSelectedPage(props.selectedPage)}
                </Typography>
            </Toolbar>
        </AppBar>
    )
};