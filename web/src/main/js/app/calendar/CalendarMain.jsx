import React from 'react'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'

import '../../../resources/static/main.scss' // webpack must be configured to do this

export const CalendarMain = () => {
    return (
        <FullCalendar defaultView="dayGridMonth" plugins={[dayGridPlugin]}/>
    )
};